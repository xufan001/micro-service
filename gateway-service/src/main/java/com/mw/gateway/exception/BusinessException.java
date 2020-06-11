package com.mw.gateway.exception;

import com.mw.gateway.enums.Code;
import lombok.Data;

/**
 * @author xufan1
 */
@Data
public class BusinessException extends RuntimeException {

    public enum Level{

        INFO("信息"), WARN("警告"), ERROR("错误");

        private String name;

        Level(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public BusinessException(Code baseCode){
        super(baseCode.getValue());
        this.baseCode = baseCode;
    }

    public BusinessException(String codestr, String msg){
        super(msg);
        this.codestr = codestr;
        this.msg = msg;
    }

    private Code baseCode;

    private Level level;

    private Object dataObj;

    private String codestr;
    private String msg;
}
