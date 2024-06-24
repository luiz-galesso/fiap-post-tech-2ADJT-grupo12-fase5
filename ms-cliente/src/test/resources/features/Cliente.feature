# language: pt

Funcionalidade: Cliente

  @high
  Cenario: Cadastrar Cliente
    Quando cadastrar um novo cliente
    Entao o cliente é registrado com sucesso
    E deve ser apresentado

  Cenario: Buscar Cliente
    Dado que um cliente ja foi cadastrado
    Quando efetuar a busca pelo email do cliente
    Entao o cliente é exibido com sucesso

  @low
  Cenario: Atualizar Cliente
    Dado que um cliente ja foi cadastrado
    Quando efetuar requisição para atualização do cliente
    Entao o cliente é atualizado com sucesso
    E deve ser apresentado

  Cenario: Remover Cliente
    Dado que um cliente ja foi cadastrado
    Quando requisitar a remoção do cliente
    Entao o cliente é removido com sucesso
