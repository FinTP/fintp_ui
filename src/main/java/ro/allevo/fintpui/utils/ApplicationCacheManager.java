/**
 * Class in which all caches and their configurations are managed
 */
package ro.allevo.fintpui.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class ApplicationCacheManager implements InitializingBean, DisposableBean{

	public static final String NUMBER_OF_MESSAGES_IN_QUEUES = "numberMessages";
	public static final String USERS = "users";
	//private CacheManager manager = CacheManager.getInstance();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		Cache numberOfMessagesCache = new Cache(new CacheConfiguration(
				NUMBER_OF_MESSAGES_IN_QUEUES, 100).eternal(false)
				.timeToLiveSeconds(200)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU));
		CacheManager.getInstance().addCacheIfAbsent(numberOfMessagesCache);
		
		Cache usersCache = new Cache(new CacheConfiguration(
				USERS, 100).eternal(false)
				.timeToLiveSeconds(2)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU));
		CacheManager.getInstance().addCacheIfAbsent(usersCache);
		
	}

	@Override
	public void destroy() throws Exception {
		CacheManager.getInstance().removeAllCaches();
		CacheManager.getInstance().shutdown();
	}
}
