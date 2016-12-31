package Payroll.AgileSD;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

	public ChangeClassificationTransaction(int empId) {
		super(empId);
	}

	protected abstract PaymentClassification getClassification();
	protected abstract PaymentSchedule getSchedule();
	
	@Override
	protected void change(Employee e) {
		e.setClassification(getClassification());
		e.setSchedule(getSchedule());
	}
}
