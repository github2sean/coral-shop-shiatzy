package schedule.order;

import com.dookay.coral.common.utils.DateUtils;

import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        List<OrderDomain> orderDomainList = orderService.getUnpaidOrder();
        Calendar calendar = Calendar.getInstance();
        System.out.println("时间："+new Date()+" listSize:"+orderDomainList);
        orderDomainList.forEach(n -> {
            try {
                Date orderTime = n.getOrderTime();
                calendar.setTime(orderTime);
                calendar.add(Calendar.HOUR_OF_DAY,1);
                Date afterTime = calendar.getTime();
                System.out.println("取消时间："+afterTime);
                Date nowTime = new Date();
                if(afterTime.before(nowTime)){
                    orderService.delete(n.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
