package com.library.exception;

public class ErrorCode {
    public final static int notFound = 80931;
    public final static int notPermision = notFound + 1;
    public final static int notEnoughBook = notFound + 2;
    public final static int DuplicateRequest = notFound + 3;
    public final static int registerBook = notFound + 4;
    public final static int borowBook = notFound + 5;
    public final static int unregisterBook = notFound + 6;

    public static String getErrorMessage(int code) {
        switch (code) {
            case notFound:
                return "Not found this book";

            case notPermision:
                return "Don't have permission";

            case notEnoughBook:
                return "This book is exhausted";
            case DuplicateRequest:
                return "this Request is duplicate ";
            case registerBook:
                return "please return book before when you want to borrow";
            case borowBook:
                return "please register before borrow book";
            case unregisterBook:
                return "this user can't register because don't register before";
        }
        return "";
    }
}
