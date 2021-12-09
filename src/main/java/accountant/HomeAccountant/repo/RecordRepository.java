package accountant.HomeAccountant.repo;

import accountant.HomeAccountant.models.Record;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<Record, Long> {

}
