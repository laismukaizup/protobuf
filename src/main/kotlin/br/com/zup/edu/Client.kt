package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val request = FuncionarioRequest.newBuilder()
        .setNome("Yuri Matheus")
        .setCpf("123123-00")
        .setSalario(2000.20)
        .setIdade(23)
        .setAtivo(true)
        .setCargo(Cargo.DEV)
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("Rua das Tabajaras")
                .setCep("00000-000")
                .setComplemento("Casa 20")
                .build()
        )
        .build()



    val client = FuncionarioServiceGrpc.newBlockingStub(channel)
    val response = client.cadastrar(request)

    println(response)
}