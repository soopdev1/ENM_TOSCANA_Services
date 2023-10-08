/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.rest;

/**
 *
 * @author Administrator
 */
public class Response_insertanagrafica {
    msg_Response_insertanagrafica msg;
    String operationStatus,operationMessage;

    public Response_insertanagrafica(msg_Response_insertanagrafica msg, String operationStatus, String operationMessage) {
        this.msg = msg;
        this.operationStatus = operationStatus;
        this.operationMessage = operationMessage;
    }

    public Response_insertanagrafica() {
    }

    public msg_Response_insertanagrafica getMsg() {
        return msg;
    }

    public void setMsg(msg_Response_insertanagrafica msg) {
        this.msg = msg;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }
        
}