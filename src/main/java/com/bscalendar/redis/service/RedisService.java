package com.bscalendar.redis.service; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service; 

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

   
     //Redis에 데이터 저장하기 (SET)
     
    public void saveData(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        
        // (참고) 10분 뒤에 자동으로 삭제되게 하려면
        // ops.set(key, value, 10, TimeUnit.MINUTES); 
    }

    
      //Redis에서 데이터 읽어오기 (GET)
     
    public String getData(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key); // key로 value를 찾아 리턴
    }
}