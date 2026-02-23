package com.ims.inventory_management_system_backend.service.sales;

import com.ims.inventory_management_system_backend.dto.sales.SaleItemsRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleItemsResponseDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleRequestDTO;
import com.ims.inventory_management_system_backend.dto.sales.SaleResponseDTO;
import com.ims.inventory_management_system_backend.entities.sale.PaymentStatus;
import com.ims.inventory_management_system_backend.entities.sale.SaleItems;
import com.ims.inventory_management_system_backend.entities.sale.SaleStatus;
import com.ims.inventory_management_system_backend.entities.sale.Sales;
import com.ims.inventory_management_system_backend.repository.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSales() {
        return salesRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Long id) {
        return mapToResponse (salesRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found")));
    }

    @Transactional(readOnly = true)
    public List<SaleItemsResponseDTO> getSaleItems(Long saleId) {
        return salesRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"))
                .getSaleItems()
                .stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO request) {
        Sales sale = mapRequestToEntity(request);
        Sales savedSale = salesRepository.save(sale);
        return mapToResponse(savedSale);
    }

    @Transactional
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO request) {
        Sales sale = salesRepository.findById(id)
                .map(existingSale -> {
                    existingSale.setReferenceNumber(request.getReferenceNumber());
                    existingSale.setDate(request.getDate());
                    existingSale.setShipping(request.getShipping());
                    existingSale.setPaid(request.getPaid());
                    existingSale.setSaleStatus(SaleStatus.valueOf(request.getSaleStatus().toUpperCase()));
                    existingSale.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()));
                    existingSale.setRemarks(request.getRemarks());
                    existingSale.setSaleItems(mapItemsToEntity(request.getItems()));
                    return existingSale;
                })
                .orElseGet(() -> {
                    Sales newSale = mapRequestToEntity(request);
                    newSale.setId(id);
                    return newSale;
                });

        Sales updatedSale = salesRepository.save(sale);
        return mapToResponse(updatedSale);
    }

    @Transactional
    public void deleteSale(Long id) {
        salesRepository.deleteById(id);
    }

    private Sales mapRequestToEntity(SaleRequestDTO request) {
        return Sales.builder()
                .referenceNumber(request.getReferenceNumber())
                .date(request.getDate())
                .shipping(request.getShipping())
                .paid(request.getPaid())
                .saleStatus(SaleStatus.valueOf(request.getSaleStatus().toUpperCase()))
                .paymentStatus(PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase()))
                .remarks(request.getRemarks())
                .saleItems(mapItemsToEntity(request.getItems()))
                .build();
    }

    private List<SaleItems> mapItemsToEntity(List<SaleItemsRequestDTO> items) {
        return items.stream().map(itemDto ->
                SaleItems.builder()
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .discount(itemDto.getDiscount())
                        .build()
        ).collect(Collectors.toList());
    }

    private SaleResponseDTO mapToResponse(Sales sale) {
        return SaleResponseDTO.builder()
                .id(sale.getId())
                .referenceNumber(sale.getReferenceNumber())
                .date(sale.getDate())
                .customerId(sale.getCustomer() != null ? sale.getCustomer().getId() : null)
                .customerName(sale.getCustomer() != null ? sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName() : null)
                .shipping(sale.getShipping())
                .paid(sale.getPaid())
                .saleStatus(sale.getSaleStatus().name())
                .paymentStatus(sale.getPaymentStatus().name())
                .remarks(sale.getRemarks())
                .subTotal(sale.getSubTotal())
                .grandTotal(sale.getGrandTotal())
                .items(sale.getSaleItems().stream().map(this::mapItemToResponse).collect(Collectors.toList()))
                .build();
    }

    private SaleItemsResponseDTO mapItemToResponse(SaleItems item) {
        return SaleItemsResponseDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .discount(item.getDiscount())
                .build();
    }
}