package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Singleton

fun main() {
    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarEndPoint())
        .build()

    server.start()
    println("startado")
    server.awaitTermination()
}

@Singleton
class FuncionarEndPoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase() {
    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {
        println(request!!)

        var nome: String? = request?.nome
        if (!request.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))) {
            nome = "[????]"
        }

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()
        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}