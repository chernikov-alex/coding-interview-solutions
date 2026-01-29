package com.alexchernikov.designpatterns.strategy.spring;

import com.alexchernikov.designpatterns.strategy.core.Storage;
import com.alexchernikov.designpatterns.strategy.core.StorageStrategy;
import com.alexchernikov.designpatterns.strategy.core.StorageType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class StorageSelector {

    private final Map<StorageType, Storage<?>> storageMap = new EnumMap<>(StorageType.class);

    public StorageSelector(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(StorageStrategy.class);

        for (Object bean : beans.values()) {
            StorageStrategy annotation = bean.getClass().getAnnotation(StorageStrategy.class);
            storageMap.put(annotation.type(), (Storage<?>) bean);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Storage<T> getStorage(StorageType type) {
        Storage<T> storage = (Storage<T>) storageMap.get(type);

        if (storage == null) {
            throw new IllegalArgumentException("No storage implementation found for type " + type);
        }

        return storage;
    }
}
