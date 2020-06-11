package com.mw.gateway.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * 通用字段
 * @author xufan1
 */
@Data
public class BaseBean implements Serializable {



    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 删除操作人
     */
    private String deleteUser;

    /**
     * 删除时间
     */
    private Date deleteDate;

    /**
     * 是否有效 删除标识
     */
    private Integer deleteFlag;


}
