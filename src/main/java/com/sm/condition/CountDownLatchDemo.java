package com.sm.condition;

import java.util.concurrent.CountDownLatch;

// 模拟 gRPC 的 StreamObserver 接口
interface StreamObserver<T> {
    void onNext(T value);
    void onError(Throwable t);
    void onCompleted();
}

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // 异步调用
        MyServiceStub stub = new MyServiceStub();; // 假设已经生成好了

        stub.listUsers(MyRequest.newBuilder().build(), new StreamObserver<MyResponse>() {
            @Override
            public void onNext(MyResponse response) {
                System.out.println("Received user: " + response.getName());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t);
                latch.countDown(); // 出错也得放行
            }

            @Override
            public void onCompleted() {
                System.out.println("All users received.");
                latch.countDown(); // 正常结束
            }
        });

        // 主线程阻塞等待，直到 onCompleted / onError 调用
        latch.await();
        System.out.println("Main thread exits after RPC call done.");
    }

    private static class MyResponse {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static class MyRequest {
        private MyRequest(Builder builder) {
        }

        public static Builder newBuilder() {
            return new Builder();
        }
        
        public static class Builder {
            public MyRequest build() {
                return new MyRequest(this);
            }
        }
    }

    // mock 的服务端 stub
    private static class MyServiceStub {
        public void listUsers(MyRequest request, StreamObserver<MyResponse> observer) {
            new Thread(() -> {
                try {
                    // 模拟异步返回多个用户
                    String[] users = {"Alice", "Bob", "Charlie"};
                    for (String name : users) {
                        Thread.sleep(500); // 模拟延迟
                        MyResponse response = new MyResponse();
                        response.setName(name);
                        observer.onNext(response);
                    }
                    observer.onCompleted();
                } catch (InterruptedException e) {
                    observer.onError(e);
                }
            }).start();
        }
    }
}
