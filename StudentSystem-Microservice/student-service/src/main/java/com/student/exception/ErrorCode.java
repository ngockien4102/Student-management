package com.student.exception;

public class ErrorCode {
    public final static int notFound = 80851;
    public final static int notPermision = notFound + 1;
    public final static int notEnoughBook = notFound + 2;
    public final static int duplicateRollnumber = notFound + 3;

    public static String getErrorMessage(int code) {
        switch (code) {
            case notFound:
                return "Not found this book";

            case notPermision:
                return "Don't have permission";

            case notEnoughBook:
                return "This book is exhausted";
            case duplicateRollnumber:
                return "this roll number is duplicate";
        }
        return "";
    }
}
