package cn.fanchencloud.boot_project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by handsome programmer.
 *
 * @author chen
 * @User: chen
 * @Date: 2021/3/8
 * @Time: 1:55
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -8903651275484949738L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 用户状态 0正常 1 禁用  -1 删除
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间 -- 修改时自动更新
     */
    private Date updateTime;


}

