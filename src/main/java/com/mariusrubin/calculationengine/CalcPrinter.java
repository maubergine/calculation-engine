package com.mariusrubin.calculationengine;

import static com.mariusrubin.calculationengine.api.IncomeType.DIVIDENDS;
import static com.mariusrubin.calculationengine.api.IncomeType.EMPLOYMENT;
import static com.mariusrubin.calculationengine.api.IncomeType.INTEREST;
import static com.mariusrubin.calculationengine.api.IncomeType.PENSION_CHARGE;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL;
import static com.mariusrubin.calculationengine.api.RateLevel.ADDITIONAL_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC;
import static com.mariusrubin.calculationengine.api.RateLevel.BASIC_NIL;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER;
import static com.mariusrubin.calculationengine.api.RateLevel.HIGHER_NIL;
import static com.mariusrubin.calculationengine.util.TaxMathUtils.HUNDRED;

import com.mariusrubin.calculationengine.api.calc.TaxCalc;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

/**
 * Class that prints an approximation of the HMRC tax template to System.out for convenience use
 * during testing.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public final class CalcPrinter {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");

  private CalcPrinter() {
  }

  public static void print(final TaxCalc result, final OutputStream out) {
    //TODO Future version of this should use a proper templating engine (Freemarker/Velocity) and/or
    //make use of a logging framework.
    final var toPrint = String.format(
        """
            Pay from all employments:              %s
            plus benefits and expenses received:   %s
            minus allowable expenses:              %s
            Total from all employments:            %s
            Dividends from foreign companies:      %s
            Interest from UK banks etc.:           %s
            Total income received:                 %s
            minus Personal Allowance:              %s
                
            Total income on which tax is due: %s
                        
            Your basic rate limit has been increased by %s and %s to %s for pension payments and
            Gift Aid payments.
                
            Pay, pensions, profit etc.
                             Amount       Percentage       Total
            Basic rate:      %s           %s%%          %s
            Higher rate:     %s           %s%%          %s
            Additional rate: %s           %s%%          %s
                
            Savings interest from banks or building societies, securities, etc.
            Basic rate band at nil rate: %s    %s%%           %s
            Basic rate:      %s           %s%%          %s
            Higher rate band at nil rate: %s    %s%%           %s
            Higher rate:     %s           %s%%          %s
            Additional rate band at nil rate: %s    %s%%           %s
            Additional rate:     %s           %s%%          %s
                        
            Dividends from companies etc.
            Basic rate band at nil rate: %s    %s%%           %s
            Basic rate:      %s           %s%%           %s
            Higher rate band at nil rate: %s    %s%%           %s
            Higher rate:      %s           %s%%           %s
            Additional rate band at nil rate: %s    %s%%           %s
            Additional rate:      %s           %s%%           %s
                        
            Total income on which tax has been charged:  %s
            Income tax due:                              %s
                
            Your adjusted income was:    %s
            Your threshold income was:   %s
            Your pension allowance was:  %s
                
            Total amount on which pension charges are due: %s
            Pension charge tax due:      %s
                        
                        
            Total income tax already paid: %s
            Total tax:                      %s
                        
            Paid income tax as %%age of total tax: %s%%
                        
            Total amount due: %s
            Less amount already paid: %s
            Balance due %s: %s
                        
            Payments on account required: %s
                        
            Payments on account due %s: %s
            Total due %s: %s
                        
            Total due %s: %s
                        
            %n""",
        result.income().totalEmploymentPay(),
        result.income().totalBenefitsAndExpenses(),
        result.income().totalAllowableExpenses(),
        result.income().totalFromAllEmployments(),
        result.income().totalDividends(),
        result.income().totalInterest(),
        result.income().netIncome(),
        result.personalAllowance().allowance(),
        result.incomeTax().totalTaxExcluding(PENSION_CHARGE).amount(),
        result.basicRateAdjustment().pensionPayments(),
        result.basicRateAdjustment().giftAidPayments(),
        result.incomeTax().basicRateLimit(),
        result.incomeTax().taxOn(EMPLOYMENT, BASIC).amount(),
        result.incomeTax().taxOn(EMPLOYMENT, BASIC).rate().rate(),
        result.incomeTax().taxOn(EMPLOYMENT, BASIC).tax(),
        result.incomeTax().taxOn(EMPLOYMENT, HIGHER).amount(),
        result.incomeTax().taxOn(EMPLOYMENT, HIGHER).rate().rate(),
        result.incomeTax().taxOn(EMPLOYMENT, HIGHER).tax(),
        result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).amount(),
        result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).rate().rate(),
        result.incomeTax().taxOn(EMPLOYMENT, ADDITIONAL).tax(),
        result.incomeTax().taxOn(INTEREST, BASIC_NIL).amount(),
        result.incomeTax().taxOn(INTEREST, BASIC_NIL).rate().rate(),
        result.incomeTax().taxOn(INTEREST, BASIC_NIL).tax(),
        result.incomeTax().taxOn(INTEREST, BASIC).amount(),
        result.incomeTax().taxOn(INTEREST, BASIC).rate().rate(),
        result.incomeTax().taxOn(INTEREST, BASIC).tax(),
        result.incomeTax().taxOn(INTEREST, HIGHER_NIL).amount(),
        result.incomeTax().taxOn(INTEREST, HIGHER_NIL).rate().rate(),
        result.incomeTax().taxOn(INTEREST, HIGHER_NIL).tax(),
        result.incomeTax().taxOn(INTEREST, HIGHER).amount(),
        result.incomeTax().taxOn(INTEREST, HIGHER).rate().rate(),
        result.incomeTax().taxOn(INTEREST, HIGHER).tax(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL_NIL).amount(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL_NIL).rate().rate(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL_NIL).tax(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL).amount(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL).rate().rate(),
        result.incomeTax().taxOn(INTEREST, ADDITIONAL).tax(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC_NIL).amount(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC_NIL).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC_NIL).tax(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC).amount(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, BASIC).tax(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).amount(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER_NIL).tax(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER).amount(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, HIGHER).tax(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL_NIL).amount(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL_NIL).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL_NIL).tax(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).amount(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).rate().rate(),
        result.incomeTax().taxOn(DIVIDENDS, ADDITIONAL).tax(),

        result.incomeTax().totalTaxExcluding(PENSION_CHARGE).amount(),
        result.incomeTax().totalTaxExcluding(PENSION_CHARGE).tax(),

        result.pension().adjustedIncome(),
        result.pension().thresholdIncome(),
        result.pension().allowance(),

        result.incomeTax().totalTaxOn(PENSION_CHARGE).amount(),
        result.incomeTax().totalTaxOn(PENSION_CHARGE).tax(),

        result.paymentDue().taxPaid(),
        result.paymentDue().totalTax(),
        result.paymentDue()
              .incomeTaxAsPercentage()
              .multiply(HUNDRED)
              .setScale(2, RoundingMode.UNNECESSARY),
        result.paymentDue().totalPaymentsOnAccount(),
        result.taxPayer().paymentsMade(),
        DATE_FORMATTER.format(result.paymentDue().balance().date()),
        result.paymentDue().balance().amount(),
        result.paymentDue().paymentsOnAccountDue() ? "yes" : "no",
        DATE_FORMATTER.format(result.paymentDue().firstPayment().date()),
        result.paymentDue().firstPayment().amount(),
        DATE_FORMATTER.format(result.paymentDue().januaryTotal().date()),
        result.paymentDue().januaryTotal().amount(),
        DATE_FORMATTER.format(result.paymentDue().secondPayment().date()),
        result.paymentDue().secondPayment().amount()
    );

    new PrintStream(out).print(toPrint);

  }

  public static void print(final TaxCalc result) {
    print(result, System.out);
  }

}
