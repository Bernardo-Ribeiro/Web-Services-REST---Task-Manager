package todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import todo.model.Task;
import todo.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // GET /api/tasks -> lista todas as tarefas
    @GetMapping
    public List<Task> getAll() {
        return repo.findAll();
    }

    // GET /api/tasks/{id} -> retorna tarefa por id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        Optional<Task> task = repo.findById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/tasks -> cria nova tarefa
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        Task saved = repo.save(task);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/tasks/{id} -> atualiza tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task updated) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setDueDate(updated.getDueDate());
            existing.setStatus(updated.getStatus());
            existing.setPriority(updated.getPriority());
            Task saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/tasks/{id} -> deleta tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return repo.findById(id).map(existing -> {
            repo.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}