package com.fatec.scc.model.cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jakarta.persistence.Column;
import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {
	@NotBlank(message = "Nome é requerido")
	private String primeiroNome;

	@NotBlank(message = "Nome é requerido")
	private String ultimoNome;

	@CPF
	@Column(unique = true) // nao funciona com @Valid tem que tratar na camada de persistencia
	private String cpf;

	@Pattern(regexp = "\\(\\d{2,}\\) \\d{4,}\\-\\d{4}")
	private String telefone;

	@NotBlank(message = "O CEP é obrigatório.")
	private String cep;

	private String endereco;

	@NotBlank(message = "O número do endereço é obrigatório")
	private Integer numeroLocal;

	private String complemento;

	public ClienteDTO() {
	}

	public ClienteDTO(String primeiroNome, String ultimoNome, String cpf, String telefone, String cep, String endereco, Integer numeroLocal, String complemento) {
		this.primeiroNome = primeiroNome;
		this.ultimoNome = ultimoNome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.cep = cep;
		this.endereco = endereco;
		this.numeroLocal = numeroLocal;
		this.complemento = complemento;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumeroLocal() {
		return numeroLocal;
	}

	public void setNumeroLocal(Integer numeroLocal) {
		this.numeroLocal = numeroLocal;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cliente retornaUmCliente() {
		return new Cliente(primeiroNome, ultimoNome, cpf, telefone, cep, endereco, numeroLocal, complemento);
	}
}