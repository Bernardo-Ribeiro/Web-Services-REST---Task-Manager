package todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import todo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // consultas customizadas podem ser adicionadas aqui
}