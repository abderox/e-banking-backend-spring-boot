package com.adria.projetbackend.services.Client;

import com.adria.projetbackend.dtos.ClientDetailsDto;
import com.adria.projetbackend.dtos.CompteClient;
import com.adria.projetbackend.dtos.NewBenificiare;
import com.adria.projetbackend.dtos.TransactionsClient;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.models.Transaction;
import com.adria.projetbackend.utils.enums.TypeStatus;

import java.util.List;

public interface IClientServices {

   ClientDetailsDto getClientDto(Long id);
   List<CompteClient> consulterToutesLesComptes(Long  identity);
   List<TransactionsClient> consulterToutesLesTransactions(Long clientIdentity);
   boolean idExists(Long id);
   Client consulterClientById(Long id);
   void modifierBenificiaire(Long clientId, NewBenificiare benificiaireReq);
   void updatePassword(Long clientId, String password);

}
