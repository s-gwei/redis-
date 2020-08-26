package com.sun.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	
	private static int port = 6379;
	private static String host = "192.168.200.3";
	private static JedisPool pool;

	static{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		pool = new JedisPool(config,host,port);
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	public static boolean getLock(String lockKey,String requestId,int timeout){
		
		Jedis jedis = getJedis();
		
		String result = jedis.set(lockKey, requestId, "NX", "EX", timeout);
		if(result == "OK"){
			return true;
		}
		return false;
	}
	public static void main(String[] args){
		poolConnect();
	}
	public static void singleConnect() {
		// Jedis单实例连接
		Jedis je = new Jedis(host, port);
		String result = je.get("k2");
		System.out.println(result);
		je.close();
	}
	public static void poolConnect() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(5);

		JedisPool pool = new JedisPool(config, host, port);
		Jedis je = pool.getResource();

		String result = je.get("k2");
		System.out.println(result);

		je.close();
		pool.close();
	}
}
