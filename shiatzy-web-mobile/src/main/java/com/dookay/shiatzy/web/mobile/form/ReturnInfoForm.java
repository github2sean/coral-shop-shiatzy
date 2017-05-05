package com.dookay.shiatzy.web.mobile.form;

import com.dookay.shiatzy.web.mobile.model.ReturnReasonModel;
import com.dookay.shiatzy.web.mobile.model.ReturnReasonTypeModel;
import lombok.Data;
import java.util.List;

/**
 * Created by admin on 2017/5/3.
 */
@Data
public class ReturnInfoForm {

    private List<ReturnReasonModel> returnList;

}
