package cn.edu.nju.candleflame.rss_spider.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;

public class SpringBeanUtils {
	private ConfigurableApplicationContext cfgContext;
	private static final SpringBeanUtils INSTANCE = new SpringBeanUtils();

	private SpringBeanUtils() {
		if (INSTANCE != null) {
			throw new Error("error");
		}
	}

	public static SpringBeanUtils getInstance() {
		return INSTANCE;
	}

	private Object readResolve() {
		return INSTANCE;
	}

	public <T> T getBean(Class<T> type) {
		Assert.notNull(type);
		return this.cfgContext.getBean(type);
	}

	public String getBeanName(Class type) {
		Assert.notNull(type);
		return this.cfgContext.getBeanNamesForType(type)[0];
	}

	public boolean exitsBean(Class type) {
		Assert.notNull(type);
		return this.cfgContext.containsBean(type.getName());
	}

	public void registerBean(String beanName, Class beanClazz, Map<String, Object> propertys) {
		Assert.notNull(beanName);
		Assert.notNull(beanClazz);
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
		if (propertys != null) {
			propertys.forEach((k, v) -> {
				builder.addPropertyValue(k, v);
			});
		}

		builder.setScope("singleton");
		this.registerBean(beanName, (BeanDefinition)builder.getBeanDefinition());
	}

	public void registerBean(String beanName, Object obj) {
		Assert.notNull(beanName);
		Assert.notNull(obj);
		this.cfgContext.getBeanFactory().registerSingleton(beanName, obj);
	}

	public void registerBean(String beanName, BeanDefinition beanDefinition) {
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)this.cfgContext.getBeanFactory();
		beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
	}

	public Map<String, Object> getBeanWithAnnotation(Class<? extends Annotation> annotationType) {
		Assert.notNull(annotationType);
		return this.cfgContext.getBeansWithAnnotation(annotationType);
	}

	public void registerBean(String beanName, Class beanClazz) {
		this.registerBean(beanName, beanClazz, (Map)null);
	}

	public void setCfgContext(ConfigurableApplicationContext cfgContext) {
		this.cfgContext = cfgContext;
	}
}
