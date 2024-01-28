public class Feedback {
    public static String getMensagem(String status, String mensagem) {
        return (status.toUpperCase().equals(Status.ERRO.name())) ?
                String.format("\u001B[41mERRO:\u001B[0m \u001B[31m%s\u001B[0m", mensagem) :
                (status.toUpperCase().equals(Status.SUCESSO.name())) ?
                        String.format("\u001B[42mSUCESSO:\u001B[0m \u001B[32m%s\u001B[0m", mensagem) :
                        "";
    }

    private enum Status {
        SUCESSO, ERRO
    }
}
