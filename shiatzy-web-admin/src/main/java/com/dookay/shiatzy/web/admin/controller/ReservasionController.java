package com.dookay.shiatzy.web.admin.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.web.MediaTypes;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.message.util.EmailUtil;
import com.dookay.coral.shop.message.util.FreemarkerUtil;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.extension.ReservationExtension;
import com.dookay.coral.shop.order.query.ReservationQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IReservationService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.admin.base.BaseApiController;
import com.dookay.shiatzy.web.admin.response.goods.ListGoodsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RestController
@RequestMapping(value = "/api/reservation")
@Api(tags="reservation",value = "/api/reservation", description = "预订单相关接口")
public class ReservasionController extends BaseApiController {

    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IReservationItemService reservationItemService;
    @Autowired
    private ReservationExtension reservationExtension;
    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;
    @Autowired
    private IMessageTemplateService messageTemplateService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IStoreService storeService;

    private final static Integer ACCEPT_BACK = 3;
    private final static Integer REFUSE_BACK = 4;

    @ApiOperation(value = "获取预订单列表", httpMethod = "GET", response = ListGoodsResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<PageList<ReservationDomain>> list(@ModelAttribute ReservationQuery reservationQuery) {
        PageList<ReservationDomain> reservationDomainPageList = reservationService.getPageList(reservationQuery);
        reservationExtension.withReservationItem(reservationDomainPageList);
        reservationExtension.withCustomer(reservationDomainPageList);
        return ResponseEntity.ok().body(reservationDomainPageList);
    }

    @ApiOperation(value = "获取预订单商品", httpMethod = "GET", response = GoodsDomain.class)
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity<ReservationDomain> get(@RequestParam("id") Long id) {
        ReservationDomain reservationDomain = reservationService.get(id);
        reservationExtension.withReservationItem(reservationDomain);
        reservationExtension.withCustomer(reservationDomain);
        reservationDomain.setStoreDomain(storeService.get(Long.parseLong(reservationDomain.getStoreTitle())));
        reservationDomain.setIsVisible(1);
        reservationService.update(reservationDomain);
        return ResponseEntity.ok().body(reservationDomain);
    }

    @ApiOperation(value = "创建预订单",httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity create(ReservationDomain domain) {
        domain.setCreateTime(new Date());
        reservationService.create(domain);
        return successResponse("创建成功");
    }

    @ApiOperation(value = "修改预订单", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity update(ReservationDomain domain) {
        reservationService.update(domain);
        return successResponse("编辑成功");
    }

    @ApiOperation(value = "删除预订单", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity delete(@RequestParam("id") Long id) {
        reservationService.delete(id);
        return successResponse("删除成功");
    }

    @ApiOperation(value = "预约不成功", httpMethod = "POST")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity cancel(@RequestParam("id") Long id) {

        ReservationItemDomain reservationItemDomain = reservationItemService.get(id);
        reservationItemDomain.setStatus(2);
        reservationItemService.update(reservationItemDomain);
        ReservationDomain reservationDomain =  reservationService.get(reservationItemDomain.getReservationId());
        reservationDomain.setStatus(1);
        reservationService.update(reservationDomain);
        reservationDomain.setStoreDomain(storeService.get(Long.parseLong(reservationDomain.getStoreTitle())));

        List<ReservationItemDomain> requestList = new ArrayList<>();
        requestList.add(reservationItemDomain);
        Double disPrice = reservationItemDomain.getGoodsDisPrice();
        Integer num = reservationItemDomain.getNum();
        Double totalAmt  = disPrice!=null?disPrice*num:reservationItemDomain.getGoodsPrice()*num;
        //发送取消预约的短信及邮件
        CustomerDomain customerDomain = customerService.get(reservationDomain.getCustomerId());
        //发送短信
        Boolean isEN = "CNY".equals(reservationDomain.getCurrentCode())?false:true;
        String phone = reservationDomain.getTel();
        if(StringUtils.isNotBlank(phone)){
            smsService.sendToSms(isEN,reservationDomain.getTel(), MessageTypeEnum.FAILED_RESERVATION.getValue());
        }
        //发送邮件
        //1.查询发送内容
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.FAILED_RESERVATION.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //2.生成模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String,Object> freeMap = new HashMap<>();
        freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        freeMap.put("name",customerDomain.getEmail());
        freeMap.put("status", isEN?"FAILED":MessageTypeEnum.FAILED_RESERVATION.getDescription());
        freeMap.put("content",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
        freeMap.put("date",simpleDateFormat.format(reservationDomain.getCreateTime()));
        freeMap.put("order",reservationDomain);
        freeMap.put("orderItem",requestList);
        reservationService.reservationWithGoodItem(requestList);
        freeMap.put("totalFee",totalAmt);
        freeMap.put("openDate",reservationDomain.getStoreDomain().getTime());
        String html = FreemarkerUtil.printString(isEN?"reservationFailed_en.ftl":"reservationFailed.ftl",freeMap);
        //3.设置发送邮件参数
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


        return successResponse("操作成功");
    }

    @ApiOperation(value = "预约成功", httpMethod = "POST")
    @RequestMapping(value = "/agree", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public ResponseEntity agree(@RequestParam("id") Long id) {
        ReservationItemDomain reservationItemDomain = reservationItemService.get(id);
        reservationItemDomain.setStatus(1);
        reservationItemService.update(reservationItemDomain);
        ReservationDomain reservationDomain =  reservationService.get(reservationItemDomain.getReservationId());
        reservationDomain.setStatus(1);
        reservationService.update(reservationDomain);

        return successResponse("预约成功");
    }

}
