package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.well.thiagos.entidades.Usuario;

public class AssertTest {
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de comparação: ", 1, 1);
		Assert.assertEquals(0.51, 0.51, 0.01);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Assert.assertNotEquals("bola", "casa");
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2;
		Usuario u4 = null;
		
		Assert.assertEquals(u1, u2);
		
		//Verificar se é a mesma instância
		Assert.assertSame(u2, u3);
		Assert.assertNotSame(u1, u2);
		
		//verificar se um objeto é nulo
		Assert.assertTrue(u4 == null);
		Assert.assertNull(u4);
		
		Assert.assertNotNull(u1);
	}
	
}
