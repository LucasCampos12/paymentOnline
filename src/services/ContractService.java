package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {
	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {

		this.onlinePaymentService = onlinePaymentService;
	}

//Metodo que recebe como argumento um contrato e o numero de meses
//Metodo responsavel por calcular as parcelas para pagamento
	public void processContract(Contract contract, int months) {

		double basicQuota = contract.getTotalValue() / months;

		for (int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);

			double interest = onlinePaymentService.interest(basicQuota, i);
			double fee = onlinePaymentService.paymentFee(basicQuota + interest);
			double total = basicQuota + fee + interest;
			
			
			contract.getInstallments().add(new Installment(dueDate, total));

		}

	}
}
