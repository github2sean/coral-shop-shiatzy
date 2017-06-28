package schedule.order;

import com.dookay.coral.common.utils.DateUtils;

import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by YinQichao on 2017-05-08.
 */
@Component
public class OrderCancelJob {

    @Autowired
    private IOrderService orderService;

    @Scheduled(cron = "0/10 * * * * *")
    public void cancel() {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setStatus(OrderStatusEnum.UNPAID.getValue());
        orderQuery.setPaymentMethod(4);
        List<OrderDomain> orderDomainList = orderService.getList(orderQuery);
        System.out.println("时间："+new Date());
        orderDomainList.forEach(n -> {
            try {
                Date orderTime =   n.getOrderTime();
                Long cancelTime =   orderTime.getTime()+60*60*1;
                System.out.println("取消时间："+new Date(cancelTime));
                Date nowTime = new Date();
                if(cancelTime<nowTime.getTime()){
                    orderService.delete(n.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
