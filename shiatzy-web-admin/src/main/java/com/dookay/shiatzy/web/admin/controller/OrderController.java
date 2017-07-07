package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.extension.GoodsCategoryExtension;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.extension.OrderExtension;
import com.dookay.coral.shop.order.form.SendGoodsForm;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.exception.ValidException;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import com.dookay.shiatzy.web.admin.utils.FreemarkerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/order")
@Api(tags="order",value = "/api/order", description = "订单相关接口")
public class OrderController extends BaseApiController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;
    @Autowired
    private IMessageTemplateService messageTemplateService;
    @Autowired
    private OrderExtension orderExtension;
    @Autowired
    private ICustomerService customerService;

    private static Integer HAVE_SEND = 3;

    @ApiOperation(value = "获取订单列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<OrderDomain>> list(@ModelAttribute OrderQuery orderQuery) {
        PageList<OrderDomain> orderDomainPageList = orderService.getPageList(orderQuery);
        orderExtension.withOrderItem(orderDomainPageList);
        return ResponseEntity.ok().body(orderDomainPageList);
    }

    @ApiOperation(value = "获取订单商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<OrderDomain> get(@RequestParam("id") Long id) {
        OrderDomain orderDomain = orderService.get(id);
        orderExtension.withOrderItem(orderDomain);
        return ResponseEntity.ok().body(orderDomain);
    }

    @ApiOperation(value = "创建订单",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(OrderDomain domain) {
        domain.setOrderTime(new Date());
        orderService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改订单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(OrderDomain domain) {
        orderService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除订单", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        orderService.delete(id);
        return successResponse("删除成功");
    }

    @ApiOperation(value = "订单发货", httpMethod = "POST")
    @RequestMapping(value = "/sendGoods", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity sendGoods(@ModelAttribute SendGoodsForm sendGoodsForm) {

        OrderDomain orderDomain = orderService.get(sendGoodsForm.getOrderId());
        //填写快递单号和快递公司
        orderDomain.setShipperCompany(sendGoodsForm.getShipperCompany());
        orderDomain.setTrackingNumber(sendGoodsForm.getTrackingNumber());
        //发货时间
        orderDomain.setShippedTime(new Date());
        //修改status为3
        if(orderDomain.getStatus()!=2){
            return exception(new ValidException("订单状态异常"));
        }
        orderDomain.setStatus(HAVE_SEND);
        orderService.update(orderDomain);


        //发送短信及邮件
        sendInformation(orderDomain);
        return successResponse("操作成功");
    }


    public void sendInformation(OrderDomain order){
            if(order.getCustomerId()!=null){
                CustomerDomain customerDomain = customerService.get(order.getCustomerId());
                OrderItemQuery orderItemquery = new OrderItemQuery();
                orderItemquery.setOrderId(order.getId());
                List<OrderItemDomain> orderItemDomainList = orderItemService.getList(orderItemquery);

                Boolean isEN = "CNY".equals(order.getCurrentCode())?false:true;
                if(StringUtils.isNotBlank(order.getShipPhone())){
                    //发送短信通知
                    smsService.sendToSms(isEN,order.getShipPhone(), MessageTypeEnum.SEND_GOODS.getValue());
                }
                //发送邮件通知
                MessageTemplateQuery query = new MessageTemplateQuery();
                query.setType(1);
                query.setCode(MessageTypeEnum.SEND_GOODS.getValue());
                query.setIsValid(1);
                MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
                //emailService.sendSingleEmail(customerDomain.getEmail(),messageTemplate.getTitle(),messageTemplate.getContent());
                //生成模版
                Map<String,Object> freeMap = new HashMap<>();
                freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
                freeMap.put("title",isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
                freeMap.put("name",customerDomain.getEmail());
                freeMap.put("status", isEN?"SHIPPED":OrderStatusEnum.SHIPPED.getDescription());
                freeMap.put("content",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
                freeMap.put("date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(order.getOrderTime()));
                orderService.withGoodItme(orderItemDomainList);
                freeMap.put("order",order);
                freeMap.put("orderItem",orderItemDomainList);
                String html = FreemarkerUtil.printString(isEN?"orderSend_en.ftl":"orderSend.ftl",freeMap);

                HashMap<String,String> emailMap = new HashMap<>();
                emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
                emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,customerDomain.getEmail());
                emailMap.put(simpleAliDMSendMail.TITLE,isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
                emailMap.put(simpleAliDMSendMail.CONTENT,html);
                try {
                    simpleAliDMSendMail.sendEmail(emailMap);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
    }
}
