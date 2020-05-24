package com.shop;

import com.shop.dao.OrderMapper;
import com.shop.dao.UserMapper;
import com.shop.pojo.Card;
import com.shop.pojo.Order;
import com.shop.service.impl.BookBorrowImpl;
import com.shop.service.impl.BookServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = G2Application.class)
public class G2ApplicationTests {
	@Autowired
	private BookServiceImpl bookServiceImpl;
	@Autowired
	private BookBorrowImpl bookBorrowImpl;
	@Autowired
	private OrderMapper orderMapper;
@Autowired
private UserMapper userMapper;
	@Test
	public void contextLoads() {
		Card card = new Card();
		card.setSum(11);
		userMapper.UpdateCard(card);
	}
	@Test
	public void Order() throws ParseException {
		Order order=new Order();
		order.setAddress("666");

		Date d=new Date();
		Date d2=new Date();
		GregorianCalendar gc =new GregorianCalendar();
		SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
              d=sf.parse("2002-11-11");
              d2=sf.parse("2001-11-11");
              long time=d.getTime()-d2.getTime();
//              sf.parse(time);
//		gc.setTime(time);

		System.out.println("time:"+time/1000/60/60/24);

// 		bookBorrowImpl.BorrowInsert(order);
		System.out.println("执行了");
	}

	@Test
	public void date() {
		Date d=new Date();
		SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
//		sf.format(d.getTime()-60 * 24 * 60 * 60 * 1000);

		System.out.println("日期为："+sf.format(d.getTime()+30 * 24 * 60 * 60 * 1000L));
	}

	@Test
	public void concat() {
	String a="aa";
	int b=1;
		String c=a.concat(a);
		String s=c.concat(String.valueOf(b));
		System.out.println(s);
	}

//	@Test
//	public void con(){
//		Order();
//		List<Order> orders = orderMapper.SelectOrderAndCart(order);
//		System.out.println("o:"+orders);
//	}



}
