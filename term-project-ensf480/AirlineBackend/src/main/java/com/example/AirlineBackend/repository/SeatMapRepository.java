    package com.example.AirlineBackend.repository;

    import com.example.AirlineBackend.model.SeatMap;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
@Repository
public interface SeatMapRepository extends JpaRepository<SeatMap, Long> {
}