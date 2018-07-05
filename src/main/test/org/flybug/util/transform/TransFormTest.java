package org.flybug.util.transform;

import static org.junit.Assert.*;

/**
 *
 * @author xdp
 * @date 2018/7/5
 *
 */
import entites.Customer;
import entites.CustomerVo;
import org.flybug.util.transform.impl.SimpleTransForm;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Target;


public class TransFormTest {

    Customer customer;

    TransForm transForm;
    @Before
    public void init(){
        transForm=new SimpleTransForm();
        customer = new Customer();
        customer.setAge(15);
        customer.setPassword("123456");
        customer.setEmail("11139712@qq.com");
        customer.setState(0);
        customer.setName("jack");
    }
    @Test
    public void transform(){
        CustomerVo customerVo= transForm.transform(customer, CustomerVo.class);

        System.out.println(customerVo);
    }
}