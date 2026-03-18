package com.hellFire.video_service.controller;

import com.hellFire.video_service.dto.CreateRoomRequest;
import com.hellFire.video_service.dto.JoinRoomRequest;
import com.hellFire.video_service.dto.RoomResponse;
import com.hellFire.video_service.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid CreateRoomRequest request) {
        log.info("Create room request: interviewId={}, hostId={}", request.getInterviewId(), request.getHostId());
        RoomResponse response = roomService.createRoom(request.getInterviewId(), request.getHostId(), request.getHostName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<RoomResponse> joinRoom(@PathVariable String roomId,
                                                 @RequestBody @Valid JoinRoomRequest request) {
        log.info("Join room request: roomId={}, userId={}", roomId, request.getUserId());
        RoomResponse response = roomService.joinRoom(roomId, request.getUserId(), request.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable String roomId) {
        log.info("Get room details: roomId={}", roomId);
        RoomResponse response = roomService.getRoom(roomId);
        return ResponseEntity.ok(response);
    }
}

