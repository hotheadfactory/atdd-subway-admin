package wooteco.subway.admin.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.StationCreateRequest;
import wooteco.subway.admin.dto.StationResponse;
import wooteco.subway.admin.service.StationService;

@RestController
public class StationController {
	private final StationService stationService;

	public StationController(StationService stationService) {
		this.stationService = stationService;
	}

	@CrossOrigin(origins="http://localhost:8090")
	@PostMapping("/stations")
	public ResponseEntity<StationResponse> createStation(@RequestBody StationCreateRequest view) {
		Station station = stationService.save(view.toStation());

		return ResponseEntity
			.created(URI.create("/stations/" + station.getId()))
			.body(StationResponse.of(station));
	}

	@CrossOrigin(origins="http://localhost:8090")
	@GetMapping("/stations")
	public ResponseEntity<List<StationResponse>> showStations() {
		return ResponseEntity.ok()
			.body(StationResponse.listOf(stationService.findAll()));
	}

	@CrossOrigin(origins="http://localhost:8090")
	@DeleteMapping("/stations/{id}")
	public ResponseEntity<StationResponse> deleteStation(@PathVariable Long id) {
		stationService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
