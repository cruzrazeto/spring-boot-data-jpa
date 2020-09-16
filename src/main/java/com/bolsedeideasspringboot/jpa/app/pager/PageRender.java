package com.bolsedeideasspringboot.jpa.app.pager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	private List<PageItem> paginas;
	private int totalPaginas;
	private int numElementosXpagina;
	private int paginaActual;

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<>();
		numElementosXpagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() +1;

		int desde, hasta;
		int pivoteMin = 0, pivoteMax = 0;
		if (totalPaginas <= numElementosXpagina ) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= numElementosXpagina / 2) {
				desde = 1;
				hasta = numElementosXpagina;
			} else if (paginaActual >= totalPaginas - numElementosXpagina / 2) {
				desde = totalPaginas - numElementosXpagina + 1;
				hasta = numElementosXpagina;
			} else {
				desde = paginaActual - numElementosXpagina / 2;
				hasta = numElementosXpagina;
			}
			int lastpage = (desde + hasta);
			if ( hasta > 5  && totalPaginas - lastpage > numElementosXpagina) {
				pivoteMax =  (totalPaginas + lastpage) / 2;
				hasta = hasta - 1;
			}
			if ( hasta > 5  && desde > numElementosXpagina) {
				pivoteMin =  (desde + 1) / 2;
				desde = desde + 1;
				hasta = hasta - 1;
			}
		}
		if (pivoteMin != 0) {
			paginas.add(new PageItem(pivoteMin, false));
		}
		for ( int i = 0;i < hasta ; i++) {
			paginas.add(new PageItem(desde + i , paginaActual == desde + i));
		}
		if (pivoteMax != 0) {
			paginas.add(new PageItem(pivoteMax, false));
		}
	}

	public String getUrl() {
		return url;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public boolean isFirst() {
		return page.isFirst();
	}
	public boolean isLast() {
		return page.isLast();
	}
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevius() {
		return page.hasPrevious();
	}
}
