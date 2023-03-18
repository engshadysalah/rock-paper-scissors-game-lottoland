package com.lottoland.infrastructure.rest;

import com.lottoland.application.service.GameService;
import com.lottoland.domain.api.RoundsPerSingleSessionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping(path = "/api/v1/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

	private final GameService gameService;
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping (path = "/play")
	public RoundsPerSingleSessionDTO playAndGetAllRoundsDetailsPerSingleSession(HttpServletRequest request) {

		String sessionId = request.getSession().getId();

		return gameService.playAndGetAllRoundsDetailsPerSingleSession(sessionId);
	}


	@PostMapping(path = "/restart")
	public void restartGame(HttpServletRequest request) {

		String sessionId = request.getSession().getId();

		gameService.restartGame(sessionId);
	}


	@GetMapping (path = "/get/allRoundsPerSingleSession")
	public HashMap<String, AtomicInteger> getAllRoundsForAllSessions() {

		return gameService.getAllRoundsForAllSessions();
	}
}