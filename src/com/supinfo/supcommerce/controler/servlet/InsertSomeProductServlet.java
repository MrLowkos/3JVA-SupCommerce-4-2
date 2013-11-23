package com.supinfo.supcommerce.controler.servlet;

import java.io.IOException;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.supcommerce.model.entity.Product;

/**
 * Servlet implementation class InsertSomeProductServlet
 * 
 * Register a random generated product in memory (via SupCommerce.jar)
 * 
 * @author Elka
 * @version 4.1
 * @since SupCommerce 2.1
 */
@WebServlet(description = "Servlet To Insert Random Product", urlPatterns = "/auth/basicInsert")
public class InsertSomeProductServlet extends HttpServlet {
	private static final long		serialVersionUID		= 1L;

	private static final String		LIST_PRODUCT_SERVLET	= "/listProduct";

	private EntityManagerFactory	emf;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertSomeProductServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// Original process
		super.init();

		// Instanciate EntityManagerFactory, only once
		emf = Persistence.createEntityManagerFactory("supcommerce-pu");
	}

	/**
	 * @see Servlet#destroy()
	 */
	@Override
	public void destroy() {
		// Close EMF
		emf.close();

		// Original process
		super.destroy();
	}

	/**
	 * Handles all HTTP methods (GET, POST, PUT, DELETE, ...).
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 * 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Random generator + protect the reference (optional)
		final Random rand = new Random();

		// New empty product
		final Product product = new Product();
		// Set product properties randomly
		product.setName("Product-" + rand.nextInt(100));
		product.setContent("Product's Information of " + product.getName());
		product.setPrice(rand.nextFloat() + rand.nextInt(1000));

		// New EntityManager generated through EMF
		final EntityManager em = emf.createEntityManager();

		// New transaction
		final EntityTransaction t = em.getTransaction();

		try {
			// Start transaction
			t.begin();

			// Save in database
			em.persist(product);

			// Commit transaction
			t.commit();
		} finally {
			// Transaction pending -> Rollback
			if (t.isActive())
				t.rollback();
			// Close EM
			em.close();
		}

		// Redirect to list product
		response.sendRedirect(request.getServletContext().getContextPath()
				+ LIST_PRODUCT_SERVLET);
	}

}
