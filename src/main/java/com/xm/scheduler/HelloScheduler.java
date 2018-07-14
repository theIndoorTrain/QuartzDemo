package com.xm.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.xm.job.HelloJob;

public class HelloScheduler {

	public static void main(String[] args) throws SchedulerException {
		/**
		 * 1.定义一个JobDetail
		 *2.绑定 HelloJob
		 *3.定义id，group
		 */
		JobDetail jobDetail = JobBuilder
				.newJob(HelloJob.class)
				.withIdentity("job1", "group1")
				.build();
		
		/**
		 * 1.获取系统时间
		 * 2.定义开始时间
		 * 3.定义结束时间
		 */
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date nowTime = new Date();
		System.out.println("Now Time:"+sf.format(nowTime));
		Date startTime = new Date(nowTime.getTime()+5000);
		System.out.println("Start Time:"+sf.format(startTime));
		Date endTime = new Date(nowTime.getTime()+30000);
		System.out.println("End Time:"+sf.format(endTime));
		
		/**
		 * 1.定义一个trigger
		 * 2.绑定group，name
		 * 3.指定开始、结束时间
		 * 4.定义任务细节
		 */
		Trigger trigger = TriggerBuilder
				.newTrigger()
				//绑定group，name
				.withIdentity("trigger1", "group1")
				//从现在开始执行
				//.startNow()
				//指定开始执行时间
				.startAt(startTime)
				//指定结束执行时间
				.endAt(endTime)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
							//执行间隔时间
							.withIntervalInSeconds(5)
							//重复执行
							.repeatForever()
				)
				.build();
		
		/**
		 * 1.定义一个SchedulerFactory工厂类
		 * 2.获得一个Scheduler类
		 * 3.启动Scheduler
		 * 4.绑定JobDetail和Trigger
		 */
		//定义一个SchedulerFactory工厂类
		SchedulerFactory  factory = new StdSchedulerFactory() ;
		//获得一个Scheduler类
		Scheduler scheduler = factory.getScheduler();
		//启动Scheduler
		scheduler.start();
		//绑定JobDetail和Trigger
		scheduler.scheduleJob(jobDetail, trigger);	
		
	}

}
