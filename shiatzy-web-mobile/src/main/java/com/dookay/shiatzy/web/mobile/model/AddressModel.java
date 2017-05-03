package com.dookay.shiatzy.web.mobile.model;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@Data
@Entity
public class AddressModel {

    private Long id;

    private String name;

    /*名*/
    private String firstName;

    /*姓*/
    private String lastName;

    /*称呼*/
    private String title;

    /*电话*/
    private String phone;

    /*国家id*/
    private Long countryId;

    /*省id*/
    private Long provinceId;

    /*城市id*/
    private Long cityId;

    /*详细地址*/
    private String address;

    /*备注*/
    private String memo;

    /*客户id*/
    private Long customerId;

}
