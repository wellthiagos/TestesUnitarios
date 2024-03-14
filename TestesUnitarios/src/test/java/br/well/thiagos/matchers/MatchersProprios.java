package br.well.thiagos.matchers;

import java.util.Calendar;

public class MatchersProprios {
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	};
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.SUNDAY);
	};

	public static DataDiferencaDiasMatcher ehHojeComDiferencaDia(Integer quantidadeDias) {
		return new DataDiferencaDiasMatcher(quantidadeDias);
	}
	
	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0);
	}
}
