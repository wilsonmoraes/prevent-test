package br.com.crdd.common.mapper;


import br.com.crdd.common.exception.InfrastructureException;
import br.com.crdd.common.pagination.PageResult;
import br.com.crdd.common.pagination.SearchParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
public class ControllerManager {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Converte um DTO em entity
     *
     * @param dto
     * @return
     */
    public <DTO extends BaseDTO, T extends BaseEntity<Long>> T dtoToEntity(DTO dto, Class<T> beanClass) {
        return Objects.nonNull(dto) ? modelMapper.map(dto, beanClass) : null;
    }

    /**
     * Converte uma entity em um DTO
     *
     * @param entity
     * @return
     */
    public <DTO extends BaseDTO, T extends BaseEntity<Long>> DTO entityToDto(T entity, Class<DTO> dtoClass) {
        return Objects.nonNull(entity) ? modelMapper.map(entity, dtoClass) : null;
    }

    /**
     * Prepara um PageRequest
     *
     * @param searchParams
     * @return
     */
    public PageRequest toPageRequest(SearchParams searchParams) {
        List<Order> orders = new ArrayList<>();
        if (Objects.nonNull(searchParams.getSorting())) {
            for (Map.Entry<String, String> entry : searchParams.getSorting().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isBlank(key) ||
                        key.equalsIgnoreCase("undefined") ||
                        key.equalsIgnoreCase("null")) {
                    continue;
                }
                orders.add(new Order(Sort.Direction.fromString(value), key));
            }

        }
        if (orders.isEmpty()) {
            return PageRequest.of(searchParams.getPage(), searchParams.getSize());
        } else {
            return PageRequest.of(searchParams.getPage(), searchParams.getSize(), buildSort(orders));
        }

    }

    private Sort buildSort(List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            List<Order> s_orders = new ArrayList<Order>();
            for (Order o : orders) {
                Sort.Order s_o = new Sort.Order(o.getDirection(), o.getProperty());
                s_orders.add(s_o);
            }
            return new Sort(s_orders);
        } else {
            return null;
        }
    }

    /**
     * Converte um pageImple em um PageResult
     *
     * @param pageImpl
     * @param dtoClass
     * @return
     */
    public <DTO extends BaseDTO, T extends BaseEntity<Long>> PageResult<DTO> toPageResult(Page<T> pageImpl, Class<DTO> dtoClass) {
        return toPageResult(pageImpl, dtoClass, null);
    }

    /**
     * Converte um pageImpl em um PageResult
     *
     * @param pageImpl
     * @return
     */
    public <DTO extends BaseDTO, T extends BaseEntity<Long>> PageResult<DTO> toPageResult(Page<T> pageImpl, Class<DTO> dtoClass, String propertyToIgnore) {

        PageResult<DTO> result = new PageResult<>();
        result.setContent(new ArrayList<>());
        result.setSize(pageImpl.getSize());
        result.setTotalElements(pageImpl.getTotalElements());
        result.setTotalPages(pageImpl.getTotalPages());

        if (pageImpl.getContent().size() > 0 && pageImpl.getContent().get(0) instanceof BaseDTO) {
            result.getContent().addAll((List<? extends DTO>) pageImpl.getContent());
        } else {
            for (T entity : pageImpl.getContent()) {
                if (propertyToIgnore != null) {
                    try {
                        BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(entity, propertyToIgnore, null);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new InfrastructureException(e);
                    }
                }
                result.getContent().add(entityToDto(entity, dtoClass));
            }
        }
        return result;
    }

    public <DTO extends BaseDTO, T extends BaseEntity<Long>> List<DTO> entityToDto(List<T> entityList, Class<DTO> dtoClass) {

        List<DTO> result = new ArrayList<>();
        for (T entity : entityList) {
            result.add(entityToDto(entity, dtoClass));
        }

        return result;
    }


    public <DTO extends BaseDTO> DTO parse(String source, Class<DTO> dtoClass) {
        try {
            return mapper.readValue(source, dtoClass);
        } catch (IOException e) {
            throw new InfrastructureException(e);
        }
    }

    public <W> W parseAnyObject(String source, Class<W> dtoClass) {
        try {
            return mapper.readValue(source, dtoClass);
        } catch (IOException e) {
            throw new InfrastructureException(e);
        }
    }


    public String writeValueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new InfrastructureException(e);
        }
    }
}