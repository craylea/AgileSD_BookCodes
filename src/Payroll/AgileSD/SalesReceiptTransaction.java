package Payroll.AgileSD;

import java.util.Date;

public class SalesReceiptTransaction extends Transaction {

	private Date date;
	private int amount;
	private int empId;
	
	public SalesReceiptTransaction(Date date, int amount, int empId) {
		this.date = date;
		this.amount = amount;
		this.empId = empId;
	}

	@Override
	public void execute() {
		Employee e = PayrollDatabase.getEmployee(empId);
		if(e != null){
			PaymentClassification pc = e.getClassification();
			if(pc instanceof CommissionedClassification){
				CommissionedClassification cc = (CommissionedClassification) pc;
				cc.addSalesReceipt(new SalesReceipt(date, amount));
			}else{
				// TODO �����쳣���
			}
		}else{
			// TODO �����쳣���
		}
	}

}
