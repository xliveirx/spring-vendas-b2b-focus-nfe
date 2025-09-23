package br.com.joao.app.service;

import br.com.joao.app.client.NfeClient;
import br.com.joao.app.domain.Address;
import br.com.joao.app.domain.Client;
import br.com.joao.app.domain.Sale;
import br.com.joao.app.domain.SaleItem;
import br.com.joao.app.repository.SaleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NfeService {

    private final NfeClient nfeClient;
    private final SaleRepository saleRepository;
    private final ObjectMapper om;


    public NfeService(NfeClient nfeClient, SaleRepository saleRepository, ObjectMapper om) {
        this.nfeClient = nfeClient;
        this.saleRepository = saleRepository;
        this.om = om.findAndRegisterModules();
    }

    private final String issuerCnpj = "32804328000157";
    private final String issuerRazao = "BRASIL SERVICOS LTDA";
    private final String issuerFantasia = "BRASIL SERVICOS";
    private final String issuerIE = "107553562";
    private final String issuerLog = "Rua Gaspar Silveira Martins";
    private final String issuerNumero = "190";
    private final String issuerBairro = "Capuava";
    private final String issuerCidade = "Goiânia";
    private final String issuerUF = "GO";
    private final String issuerCEP = "74450-370";

    public void emitBySale(Long saleId){

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found."));

        var dt = (sale.getCreatedAt() != null ? sale.getCreatedAt() : LocalDateTime.now())
                .withNano(0).toString();

        var root = om.createObjectNode();

        // Header
        root.put("natureza_operacao", "Remessa");
        root.put("data_emissao", dt);
        root.put("data_entrada_saida", dt);
        root.put("tipo_documento", 1);
        root.put("finalidade_emissao", 1);

        // Issuer
        root.put("cnpj_emitente", onlyDigits(issuerCnpj));
        root.put("nome_emitente", issuerRazao);
        root.put("nome_fantasia_emitente", issuerFantasia);
        root.put("inscricao_estadual_emitente", onlyDigits(issuerIE));
        root.put("logradouro_emitente", issuerLog);
        root.put("numero_emitente", issuerNumero);
        root.put("bairro_emitente", issuerBairro);
        root.put("municipio_emitente", issuerCidade);
        root.put("uf_emitente", issuerUF);
        root.put("cep_emitente", onlyDigits(issuerCEP));

        // Client

        Client c = sale.getClient();
        Address a = c.getAddress();

        root.put("nome_destinatario", c.getCompanyName());
        if (c.getCnpj() != null) root.put("cnpj_destinatario", onlyDigits(c.getCnpj()));
        root.put("inscricao_estadual_destinatario", "107553562");
        root.put("telefone_destinatario", onlyDigits(c.getPhone()));
        root.put("logradouro_destinatario", a.getStreet());
        root.put("numero_destinatario", 999);
        root.put("bairro_destinatario", a.getNeighborhood());
        root.put("municipio_destinatario", a.getCity());
        root.put("uf_destinatario", a.getState());
        root.put("pais_destinatario", a.getCountry() );
        root.put("cep_destinatario", onlyDigits(a.getZip()));

        // Items (products)
        var items = root.putArray("items");
        var cfop = issuerUF.equalsIgnoreCase(a.getState()) ? "5923" : "6923";
        BigDecimal total = BigDecimal.ZERO;
        int i = 1;

        for(SaleItem si : sale.getItems()) {
            if(si.getProduct() == null) continue;

            var p = si.getProduct();
            var it = items.addObject();

            var qtd = new BigDecimal(si.getQuantity());
            var unit = si.getUnitPrice();
            var bruto = unit.multiply(qtd);

            total = total.add(bruto);

            it.put("numero_item", i++);
            it.put("codigo_produto", p.getId().toString());
            it.put("descricao", si.getName() != null ? si.getName() : "Item");
            it.put("cfop", cfop);
            it.put("unidade_comercial", "un");
            it.put("quantidade_comercial", qtd);
            it.put("valor_unitario_comercial", unit);
            it.put("valor_unitario_tributavel", unit);
            it.put("unidade_tributavel", "un");
            it.put("codigo_ncm", "00000000");
            it.put("quantidade_tributavel", qtd);
            it.put("valor_bruto", bruto);
            it.put("icms_situacao_tributaria", 400);
            it.put("icms_origem", 0);
            it.put("pis_situacao_tributaria", "07");
            it.put("cofins_situacao_tributaria", "07");
        }

        root.put("valor_produtos", total);
        root.put("valor_total", total);
        root.put("valor_frete", BigDecimal.ZERO);
        root.put("valor_seguro", BigDecimal.ZERO);
        root.put("modalidade_frete", 0);

        String ref = "SALE-" + sale.getId() + "-" + UUID.randomUUID();
        nfeClient.sendNfe(ref, root);

    }

    private static String onlyDigits(String s) { return s == null ? null : s.replaceAll("\\D+",""); }
}
