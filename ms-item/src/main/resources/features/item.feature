#language: pt

  Funcionalidade: Item
    @high
    Cenario: Cadastrar item
      Dado que tenha um item para cadastrar
      Quando eu cadastro o item
      Entao o item é cadastrado
      E deverá ser apresentado

    Cenario: Buscar item
      Dado que o item já foi cadastrado
      Quando efetuar a busca pelo ID
      Então exibir item

    Cenário: Atualizar os dados de um item existente
      Dado que o item já foi cadastrado
      E que eu deseje atualizar os dados desse item
      Quando eu atualizo o item com novas informações
      Então o item deve ser atualizado com sucesso

    Cenario: Remover item
      Dado que o item já foi cadastrado
      Quando deletar o item
      Entao o item deve ser deletado
