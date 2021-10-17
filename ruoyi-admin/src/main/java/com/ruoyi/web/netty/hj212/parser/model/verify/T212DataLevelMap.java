package com.ruoyi.web.netty.hj212.parser.model.verify;



import com.ruoyi.web.netty.hj212.parser.core.validator.clazz.FieldC;
import com.ruoyi.web.netty.hj212.parser.core.validator.clazz.FieldN;
import com.ruoyi.web.netty.hj212.parser.core.validator.clazz.FieldValidDate;
import com.ruoyi.web.netty.hj212.parser.core.validator.field.C;
import com.ruoyi.web.netty.hj212.parser.core.validator.field.N;
import com.ruoyi.web.netty.hj212.parser.core.validator.field.ValidDate;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.ModeGroup;
import com.ruoyi.web.netty.hj212.parser.model.verify.groups.VersionGroup;

import java.util.Map;

/**
 * T212 Map
 * 解决无法对MAP进行验证定义问题
 * Created on 2018/1/10.
 */
@FieldValidDate(field = "QN",
        value = @ValidDate(format = "yyyyMMddHHmmssSSS"))
@FieldC(field = "ST",
        value = @C(len = 2))
@FieldC(field = "CN",
        value = @C(len = 4))
@FieldC(field = "PW",
        value = @C(len = 6))
@FieldC(field = "MN",
        value = @C(len = 14))
@FieldN(field = "Flag",
        value = @N(integer = 3))
@FieldN(field = "PNUM", groups = ModeGroup.UseSubPacket.class,
        value = @N(integer = 4, optional = false))
@FieldN(field = "PNO", groups = ModeGroup.UseSubPacket.class,
        value = @N(integer = 4, optional = false))
@FieldC(field = "CP", groups = { VersionGroup.V2005.class },
        value = @C(len = 960))
@FieldC(field = "CP", groups = { VersionGroup.V2017.class },
        value = @C(len = 950))
public class T212DataLevelMap
        extends T212Map<String,String> {

    public T212DataLevelMap(Map<String,String> m) {
        super(m);
    }
}
