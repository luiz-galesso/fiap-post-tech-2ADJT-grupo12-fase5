# language: pt

Funcionalidade: Endereco

  @high
  Cenario: Cadastrar Endereco
    Quando cadastrar um novo endereco
    Entao o endereco é registrado com sucesso
    E deve ser apresentado o endereco

  Cenario: Buscar Endereco
    Dado que um endereco ja foi cadastrado
    Quando efetuar a busca pelo id do endereco
    Entao o endereco é exibido com sucesso

  @low
  Cenario: Atualizar Endereco
    Dado que um endereco ja foi cadastrado
    Quando efetuar requisição para atualização do endereco
    Entao o endereco é atualizado com sucesso
    E deve ser apresentado o endereco

  #Cenario: Remover Endereco
  #  Dado que um endereco ja foi cadastrado
  #  Quando requisitar a remoção do endereco
  #  Entao o endereco é removido com sucesso
