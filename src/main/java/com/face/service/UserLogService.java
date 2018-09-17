package com.face.service;

public interface UserLogService {

    String save(String userId,String sessionId);

    String getUserIdById(String id);
}
