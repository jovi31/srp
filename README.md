# Sistema de Recarga de Passe

O objetivo é fornecer um serviço que possibilte a recarga online dos créditos do cartão de passe.

Uma vez que o transporte público é um serviço amplamente utilizado no país, foram desenvolvidas tecnologias que facilitam seu uso trazendo segurança, comodidade e eficiência. Umas dessas tecnologias é a bilhetagem eletrônica que dispensa a necessidade do usuário pagar a passagem com dinheiro em espécie.

No entanto, a bilhetagem eletrônica não está relacionada a compra de créditos pela internet através de um aplicativo ou site, sendo a recarga do cartão realizada em estabelecimentos cadastrados pela empresa de transporte público. Por isso, é comum em grandes centros reclamações a respeito de grandes filas para fazer a recarga ou da distância até os estabelecimentos.

Nesse sentido, a proposta do sistema apresentado nesse documento é oferecer um serviço de recarga de passe online que possa ser implantado em municípios que possuem a tecnologia necessária para esse tipo de operação.

## Manutenção de Cadastros

```
Usuário (Cliente ou Administrador)
Cartão inteligente
Conta
UF
Cidade
Empresa
```

## Processos de Negócio

```
Recarga
Agendamento
Saque
```

## Relatórios

```
Listar totais e quantidades de Recargas nas Empresas (data início e data término)
Listar totais e quantidades de Saques nas Empresas (data início e data término)
Listar quantidade de Empresas nas Cidades (Por UF)
Listar Recargas (Por cliente, data início e data término)
Listar Saques (Por cliente, data início e data término)
```

## Documentação da API

- https://documenter.getpostman.com/view/12476534/TVCY6BYm