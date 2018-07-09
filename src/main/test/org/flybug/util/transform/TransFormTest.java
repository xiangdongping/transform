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
import java.util.Map;


public class TransFormTest {

    Customer customer;

    CustomerVo customerVo;

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
        customer.setId(100L);


        customerVo=new CustomerVo();

        customerVo.setAge(15);
        customerVo.setCode("zsd204");
        customerVo.setEmail("11139721@qq.com");
        customerVo.setName("jack");
        customerVo.setPassword("123456");
        customerVo.setId(101L);

    }
    @Test
    public void transform(){
        CustomerVo customerVo= transForm.transform(customer, CustomerVo.class);
        System.out.println(customerVo);
    }

    @Test
    public void voTransform(){

        Customer transform = transForm.transform(customerVo, Customer.class);

        System.out.println(transform);
    }

    @Test
    public void testIgnore(){

        transForm.fill(customer,customerVo);

        System.out.println(customer);
        System.out.println(customerVo);

    }
    @Test
    public void mapTest(){
        Map<String, Object> stringObjectMap = transForm.transformToMap(customerVo);

        stringObjectMap.forEach((k,v) -> {

            System.out.println(String.format("k = %s , v=%s",k,v));
        });
    }
}