package com.algaworks.wine.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.wine.model.Vinho;
import com.algaworks.wine.repository.Vinhos;
import com.algaworks.wine.storage.FotoStorage;
import com.algaworks.wine.storage.FotoStorageS3;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class CadastroVinhoService {
	@Autowired
	private Vinhos vinhos;

	@Autowired
	FotoStorage fotoStorage;

	public void salvar(Vinho vinho) {
		// Escrever regras de negocio...
		this.vinhos.save(vinho);

	}

	public String salvarFoto(Long codigo, MultipartFile foto) {
		
		String nomeFoto = fotoStorage.salvar(foto);
		
		Vinho vinho = vinhos.findOne(codigo);
		
		vinho.setFoto(nomeFoto);
		vinhos.save(vinho);
		return fotoStorage.getUrl(nomeFoto);

	}
}