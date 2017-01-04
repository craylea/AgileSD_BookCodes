package Payroll.AgileSD;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class PayrollTest extends TestCase{

	/**
	 * ���Ӵ�н��Ա
	 */
	public void testAddSalariedEmployee(){
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
		t.execute();
		
		Employee e = PayrollDatabase.getEmployee(empId);
		assert("Bob" == e.getName());
		
		PaymentClassification pc = e.getClassification();
		SalariedClassification sc = (SalariedClassification) pc;
		assertNotNull(sc);
		
		assertEquals(1000.00, sc.getSalary(), .001);
		PaymentSchedule ps = e.getSchedule();
		MonthlySchedule ms = (MonthlySchedule) ps;
		assertNotNull(ms);
		PaymentMethod pm = e.getMethod();
		HoldMethod hm = (HoldMethod) pm;
		assertNotNull(hm);
	}
	/**
	 * ɾ����Ա
	 */
	public void testDeleteEmployee(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		assertEquals("Lance", e.getName());
		
		DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId);
		dt.execute();
		e = PayrollDatabase.getEmployee(empId);
		assertNull(e);
	}
	/**
	 * ��ʱ�����Ա��ʱ�俨Ƭ
	 */
	public void testTimeCardTransaction(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		TimeCardTransaction tct = new TimeCardTransaction(new Date(2001, 10, 31), 8.0, empId);
		tct.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		
		PaymentClassification pc = e.getClassification();
		HourlyClassification hc = (HourlyClassification) pc;
		assertNotNull(hc);
		assertEquals(15.25, hc.getHourlyRate());
		TimeCard tc = hc.getTimeCard(new Date(2001, 10, 31));
		assertNotNull(tc);
		assertEquals(8.0, tc.getHours());
	}
	/**
	 * ����Ա������ƾ��
	 */
	public void testSalesReceiptTransaction(){
		int empId = 4;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "LDB", "Home", 9000.0, 100.0);
		t.execute();
		SalesReceiptTransaction srt = new SalesReceiptTransaction(new Date(2001, 10, 31), 2, empId);
		srt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		assertEquals("LDB", e.getName());
		
		PaymentClassification pc = e.getClassification();
		CommissionedClassification cc = (CommissionedClassification) pc;
		assertEquals(9000, cc.getSalary(), .001);
		assertEquals(100, cc.getCommissionRate(), .001);
		SalesReceipt sr = cc.getSalesReceipt(new Date(2001, 10, 31));
		assertNotNull(sr);
		assertEquals(2, sr.getAmount());
	}
	/**
	 * ��Ա������
	 */
	public void testAddServiceCharge(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		int memberId = 88;
		UnionAffiliation uaf = new UnionAffiliation(memberId, 12.5);
		e.setAffiliation(uaf);
		PayrollDatabase.addUnionMember(memberId, e);
		ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, new Date(2001, 10, 31), 12.95);
		sct.execute();
		ServiceCharge sc = uaf.getServiceCharge(new Date(2001, 10, 31));
		assertNotNull(sc);
		assertEquals(12.95, sc.getAmount(), .001);
	}
	/**
	 * �޸Ĺ�Ա����
	 */
	public void testChangeNameTransaction(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		ChangeNameTransaction  ct = new ChangeNameTransaction(empId, "Bill_2");
		ct.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertEquals("Bill_2", e.getName());
	}
	/**
	 * �޸Ĺ�ԱΪ��Сʱ����нˮ
	 */
	public void testChangeHourlyTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.25);
		cht.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		HourlyClassification hc = (HourlyClassification) pc;
		assertNotNull(hc);
		assertEquals(27.25, hc.getHourlyRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		WeeklySchedule ws = (WeeklySchedule) ps;
		assertNotNull(ws);
	}
	/**
	 * �޸Ĺ�ԱΪ��н
	 */
	public void testChangeSalariedTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeSalariedTransaction cht = new ChangeSalariedTransaction(empId, 9000);
		cht.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		SalariedClassification sc = (SalariedClassification) pc;
		assertNotNull(sc);
		assertEquals(9000, sc.getSalary(), .001);
		PaymentSchedule ps = e.getSchedule();
		MonthlySchedule ms = (MonthlySchedule) ps;
		assertNotNull(ms);
	}
	/**
	 * �޸Ĺ�ԱΪ����Ա
	 */
	public void testChangeCommissionedTransaction(){
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.00);
		t.execute();
		ChangeCommissionedTransaction cht = new ChangeCommissionedTransaction(empId, 9000, 100.0);
		cht.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentClassification pc = e.getClassification();
		assertNotNull(pc);
		CommissionedClassification cc = (CommissionedClassification) pc;
		assertNotNull(cc);
		assertEquals(9000, cc.getSalary(), .001);
		assertEquals(100.0, cc.getCommissionRate(), .001);
		PaymentSchedule ps = e.getSchedule();
		BiweeklySchedule bs = (BiweeklySchedule) ps;
		assertNotNull(bs);
	}
	/**
	 * �޸�֧����ʽΪ���浽����
	 */
	public void testChangeHoldTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeHoldTransaction cht = new ChangeHoldTransaction(empId);
		cht.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentMethod pm = e.getMethod();
		assertNotNull(pm);
		HoldMethod hm = (HoldMethod) pm;
		assertNotNull(hm);
	}
	/**
	 * �޸�֧����ʽΪֱ�Ӵ��������˻�
	 */
	public void testChangeDirectTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeDirectTransaction cdt = new ChangeDirectTransaction("��������", "12345", empId);
		cdt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentMethod pm = e.getMethod();
		assertNotNull(pm);
		DirectMethod dm = (DirectMethod) pm;
		assertNotNull(dm);
		assertEquals("��������", dm.getBank());
		assertEquals("12345", dm.getAccount());
	}
	/**
	 * �޸�֧����ʽΪ�ʼ�֧Ʊ
	 */
	public void testChangeMailTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeMailTransaction cmt = new ChangeMailTransaction("������ɽ������԰", empId);
		cmt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		PaymentMethod pm = e.getMethod();
		assertNotNull(pm);
		MailMethod mm = (MailMethod) pm;
		assertNotNull(mm);
		assertEquals("������ɽ������԰", mm.getAddress());
	}
	/**
	 * �޸Ĺ�ԱΪЭ���Ա
	 */
	public void testChangeMemberTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		int memberId = 86;
		ChangeMemberTransaction cmt = new ChangeMemberTransaction(memberId, 12.5, empId);
		cmt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		Affiliation af = e.getAffiliation();
		assertNotNull(af);;
		UnionAffiliation uaf = (UnionAffiliation) af;
		assertNotNull(uaf);
		assertEquals(12.5, uaf.getDues(), .001);
		Employee e2 = PayrollDatabase.getUnionMember(memberId);
		assertEquals(e, e2);
	}
	/**
	 * �޸Ĺ�ԱΪ����Э���Ա
	 */
	public void testChangeUnaffiliatedTransaction(){
		int empId = 3;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
		t.execute();
		ChangeUnaffiliatedTransaction cmt = new ChangeUnaffiliatedTransaction(empId);
		cmt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		Affiliation af = e.getAffiliation();
		assertNotNull(af);;
		NoAffiliation naf = (NoAffiliation) af;
		assertNotNull(naf);
	}
	/**
	 * ��н�ռ�����н��Աнˮ
	 */
	public void testPaySingleSalariedEmployee(){
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
		t.execute();
		Date payDate = new Date(116, 11, 31);
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		Paycheck pc = pt.getPaycheck(empId);
		assertNotNull(pc);
//		assertEquals(payDate, pc.getPayDate());
		assertEquals(1000.0, pc.getGrossPay(), .001);
//		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(0.0, pc.getDeductions(), .001);
		assertEquals(1000.0, pc.getNetPay(), .001);
	}
	/**
	 * �Ƿ�н�ռ�����н��Աнˮ
	 */
	public void testPaySingleSalariedEmployeeOnWrongDate(){
		int empId = 1;
		AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
		t.execute();
		Date payDate = new Date(116, 11, 30);
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		Paycheck pc = pt.getPaycheck(empId);
		assertNull(pc);
	}
	/**
	 * ��н�ռ���û��ʱ�俨���ӵ㹤��нˮ
	 */
	public void testPaySingleHourlyEmployeeNoTimeCards(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		Calendar c = Calendar.getInstance();
		c.set(2016, 11, 30);
		Date payDate = c.getTime();
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		validatePaycheck(pt, empId, payDate, 0.0);
	}
	/**
	 * ��н�ռ���ֻ��һ��ʱ�俨�ҹ�ʱС��8Сʱ���ӵ㹤��нˮ
	 */
	public void testPaySingleHourlyEmployeeOneTimeCards(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		Date payDate = new Date(116, 11, 30); // Friday
		TimeCardTransaction tct = new TimeCardTransaction(payDate, 2.0, empId);
		tct.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		validatePaycheck(pt, empId, payDate, 30.5);
	}
	/**
	 * ��н�ռ���ֻ��һ��ʱ�俨�ҹ�ʱ����8Сʱ���ӵ㹤��нˮ
	 */
	public void testPaySingleHourlyEmployeeOverTimeOneTimeCards(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		Date payDate = new Date(116, 11, 30); // Friday
		TimeCardTransaction tct = new TimeCardTransaction(payDate, 9.0, empId);
		tct.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		validatePaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
	}
	/**
	 * �ӵ㹤��ĩ�Ӱ�нˮ����
	 */
	public void testPaySingleHourlyEmployeeWithWeekendTimeCards(){
		int empId = 2;
		AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home", 15.25);
		t.execute();
		Date weekendDate = new Date(116, 11, 25); // Sunday
		TimeCardTransaction tct = new TimeCardTransaction(weekendDate, 9.0, empId);
		tct.execute();
		Date payDate = new Date(116, 11, 30); // Friday
		tct = new TimeCardTransaction(payDate, 2.0, empId);
		tct.execute();
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		validatePaycheck(pt, empId, payDate, (2 + 9 * 1.5) * 15.25);
	}
	/**
	 * ����Աнˮ����
	 */
	public void testPaySingleCommissionedEmployeeOneSalesReceipt(){
		int empId = 4;
		AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "LDB", "Home", 9000.0, 100.0);
		t.execute();
		Date workDate = new Date(116, 11, 19);
		SalesReceiptTransaction srt = new SalesReceiptTransaction(workDate, 2, empId);
		srt.execute();
		Employee e = PayrollDatabase.getEmployee(empId);
		assertNotNull(e);
		assertEquals("LDB", e.getName());
		
		PaymentClassification pc = e.getClassification();
		CommissionedClassification cc = (CommissionedClassification) pc;
		assertEquals(9000, cc.getSalary(), .001);
		assertEquals(100, cc.getCommissionRate(), .001);
		SalesReceipt sr = cc.getSalesReceipt(workDate);
		assertNotNull(sr);
		assertEquals(2, sr.getAmount());
		//Date payDate = new Date(116, 11, 30);
		Date payDate = new Date(116, 11, 31);
		PaydayTransaction pt = new PaydayTransaction(payDate);
		pt.execute();
		//validatePaycheck(pt, empId, payDate, 100.0 * 2);
		validatePaycheck(pt, empId, payDate, 9000);
	}
	
	private void validatePaycheck(PaydayTransaction pt, int empId, Date payDate, double pay) {
		Paycheck pc = pt.getPaycheck(empId);
		assertNotNull(pc);
//		assertEquals(payDate, pc.getPayPeriodEndDate());
		assertEquals(pay, pc.getGrossPay(), .001);
//		assertEquals("Hold", pc.getField("Disposition"));
		assertEquals(0.0, pc.getDeductions(), .001);
		assertEquals(pay, pc.getNetPay(), .001);
	}
}
