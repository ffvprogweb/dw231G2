package com.fatec.scc.controller.employee;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.fatec.scc.model.employee.Employee;
import com.fatec.scc.model.employee.EmployeeDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fatec.scc.services.employee.MaintainEmployeeI;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class APIEmployeeController {
	@Autowired
	MaintainEmployeeI mantemFuncionario;
	Employee employee;
	Logger logger = LogManager.getLogger(this.getClass());

	@CrossOrigin // desabilita o cors do spring security
	@PostMapping
	public ResponseEntity<Object> saveEmployee(@RequestBody @Valid EmployeeDTO employeeDTO, BindingResult result) {
		employee = new Employee();
		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller validacao da entrada dados invalidos" + result.getFieldError());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}
		if (mantemFuncionario.searchByCpf(employee.getCpf()).isPresent()) {
			logger.info(">>>>>> apicontroller consultaporcpf cpf ja cadastrado");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado");
		}
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(mantemFuncionario.save(employeeDTO.returnEmployee()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro não esperado ");
		}
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<List<Employee>> FindAll() {
		return ResponseEntity.status(HttpStatus.OK).body(mantemFuncionario.searchAll());
	}

	@CrossOrigin // desabilita o cors do spring security
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id) {
		Optional<Employee> funcionario = mantemFuncionario.searchById(id);
		if (funcionario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}
		mantemFuncionario.delete(funcionario.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body("Funcionário excluido");
	}

	@CrossOrigin // desabilita o cors do spring security
	@PutMapping("/{id}")
	public ResponseEntity<Object> updates(@PathVariable long id, @RequestBody @Valid EmployeeDTO employeeDTO,
			BindingResult result) {
		logger.info(">>>>>> api atualiza informações de funcionario chamado");
		if (result.hasErrors()) {
			logger.info(">>>>>> apicontroller atualiza informações de funcionario chamado dados invalidos");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos.");
		}
		Optional<Employee> f = mantemFuncionario.searchById(id);
		if (f.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}
		Optional<Employee> func = mantemFuncionario.updates(id, employeeDTO.returnEmployee());
		return ResponseEntity.status(HttpStatus.OK).body(func.get());
	}

	@CrossOrigin // desabilita o cors do spring security
	@GetMapping("/{id}")
	public ResponseEntity<Object> searchById(@PathVariable Long id) {
		logger.info(">>>>>> apicontroller consulta por id chamado");
		Optional<Employee> funcionario = mantemFuncionario.searchById(id);
		if (funcionario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(funcionario.get());
	}
}