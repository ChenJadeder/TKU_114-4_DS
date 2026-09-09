// 課堂實作題一：Generic Result
// 指定檔名：GenericResultDemo.java
// 課堂實作題一：Generic Result
// 指定檔名：GenericResultDemo.java
class Result<T> {
    private boolean success;
    private String message;
    private T data;

    Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;

        // 失敗時不保留資料
        if (success) {
            this.data = data;
        } else {
            this.data = null;
        }
    }

    boolean isSuccess() {
        return success;
    }

    String getMessage() {
        return message;
    }

    T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "success=" + success
                + ", message=" + message
                + ", data=" + data;
    }
}

public class GenericResultDemo {
    public static void main(String[] args) {
        Result<String> textResult =
                new Result<String>(true, "read text success", "Java");

        Result<Integer> numberResult =
                new Result<Integer>(true, "read number success", 114);

        Result<String> failResult =
                new Result<String>(false, "data not found", "should disappear");

        System.out.println(textResult);
        System.out.println("upper=" + textResult.getData().toUpperCase());

        System.out.println(numberResult);
        System.out.println("number + 1=" + (numberResult.getData() + 1));

        System.out.println(failResult);
        System.out.println("fail data is null=" + (failResult.getData() == null));
    }
}
