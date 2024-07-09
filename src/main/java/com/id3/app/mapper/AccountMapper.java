package com.id3.app.mapper;

import com.id3.app.dto.AccountDto;
import com.id3.app.model.Account;
import com.id3.app.request.CreateAccountRequest;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

     Account createAccount(CreateAccountRequest request);

    AccountDto  toAccountDto(Account education);

    List<AccountDto>  toAccountDtoList(List<Account> educationList);

}
