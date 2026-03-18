package com.hellFire.video_service.service;

import com.hellFire.video_service.dto.RoomResponse;
import com.hellFire.video_service.model.Participant;
import com.hellFire.video_service.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    @Value("${video.join.base-url:http://localhost:8010/video}")
    private String baseJoinUrl;

    public RoomResponse createRoom(String interviewId, String hostId, String hostName) {
        String roomId = UUID.randomUUID().toString();
        List<Participant> participants = Collections.synchronizedList(new ArrayList<>());
        participants.add(new Participant(hostId, hostName));

        Room room = Room.builder()
                .roomId(roomId)
                .interviewId(interviewId)
                .hostId(hostId)
                .participants(participants)
                .build();

        rooms.put(roomId, room);

        String joinUrl = baseJoinUrl + "/rooms/" + roomId;
        log.info("Created room {} for interview {} by host {}", roomId, interviewId, hostId);
        return toResponse(room, joinUrl);
    }

    public RoomResponse joinRoom(String roomId, String userId, String username) {
        Room room = getRoomOrThrow(roomId);
        boolean alreadyPresent = room.getParticipants().stream()
                .anyMatch(p -> p.getUserId().equals(userId));
        if (!alreadyPresent) {
            room.getParticipants().add(new Participant(userId, username));
            log.info("User {} ({}) joined room {}", userId, username, roomId);
        }
        String joinUrl = baseJoinUrl + "/rooms/" + roomId;
        return toResponse(room, joinUrl);
    }

    public void leaveRoom(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return;
        }
        room.getParticipants().removeIf(p -> p.getUserId().equals(userId));
        log.info("User {} left room {}", userId, roomId);
        if (room.getParticipants().isEmpty()) {
            rooms.remove(roomId);
            log.info("Removed empty room {}", roomId);
        }
    }

    public RoomResponse getRoom(String roomId) {
        Room room = getRoomOrThrow(roomId);
        String joinUrl = baseJoinUrl + "/rooms/" + roomId;
        return toResponse(room, joinUrl);
    }

    private Room getRoomOrThrow(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found: " + roomId);
        }
        return room;
    }

    private RoomResponse toResponse(Room room, String joinUrl) {
        return RoomResponse.builder()
                .roomId(room.getRoomId())
                .interviewId(room.getInterviewId())
                .hostId(room.getHostId())
                .joinUrl(joinUrl)
                .participants(new ArrayList<>(room.getParticipants()))
                .build();
    }
}
